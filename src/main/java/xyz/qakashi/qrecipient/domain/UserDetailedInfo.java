package xyz.qakashi.qrecipient.domain;


import lombok.*;
import xyz.qakashi.qrecipient.domain.enums.Gender;
import xyz.qakashi.qrecipient.util.Constants;
import xyz.qakashi.qrecipient.web.dto.RegisterDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = Constants.DATABASE_PREFIX + "userInfo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailedInfo extends BaseEntity<Long> {
    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "country")
    private String country;

    @Column(name = "birthday")
    private LocalDate birth;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "about_me")
    private String about;

    @Column(name = "job")
    private String job;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public UserDetailedInfo (RegisterDto dto) {
        this.firstname = dto.getFirstname();
        this.lastname = dto.getLastname();
        this.patronymic = dto.getPatronymic();
        this.phone = dto.getPhone();
        this.country = dto.getCountry();
        this.birth = dto.getBirth();
        this.gender = dto.getGender();
        this.about = dto.getAbout();
        this.job = dto.getJob();
    }
}

