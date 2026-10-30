package eu.virac.vea_izi_vsrc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class KPISearch {
    private String keyword;

    private User creator;

    private User overlooker;

    private LocalDate creation_date;

    private LocalDate deadline;

    private boolean deadline_is_passed;

    private Category category;

    public KPISearch(String keyword, User creator, User overlooker, LocalDate creation_date, LocalDate deadline, boolean deadline_is_passed, Category category){
        this.keyword = keyword;
        this.creator = creator;
        this.overlooker = overlooker;
        this.creation_date = creation_date;
        this.deadline = deadline;
        this.deadline_is_passed = deadline_is_passed;
        this.category = category;
    }

}

