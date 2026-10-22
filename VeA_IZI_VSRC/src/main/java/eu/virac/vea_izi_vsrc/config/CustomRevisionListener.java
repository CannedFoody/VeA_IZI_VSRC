
package eu.virac.vea_izi_vsrc.config;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevision revision = (CustomRevision) revisionEntity;

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()) {
            revision.setUsername(authentication.getName());
        } else {
            revision.setUsername("SYSTEM");
        }
    }
}
