package eu.virac.vea_izi_vsrc.config;

import java.time.LocalDateTime;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomRevisionListener implements EntityTrackingRevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevision revision = (CustomRevision) revisionEntity;
        revision.setUsername(currentUser());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void entityChanged(Class entityClass, String entityName, Object entityId, RevisionType revisionType, Object revisionEntity) {
        CustomRevision revision = (CustomRevision) revisionEntity;

        RevisionChange change = new RevisionChange();
        change.setRevision(revision);
        change.setChangedAt(LocalDateTime.now());
        change.setUsername(currentUser());
        change.setEntityName(entityClass.getSimpleName());
        change.setEntityId(String.valueOf(entityId));
        change.setActionType(switch (revisionType) {
            case ADD -> "CREATED";
            case MOD -> "MODIFIED";
            case DEL -> "DELETED";
        });

        revision.getChanges().add(change);
    }

    private String currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "SYSTEM";
    }
}