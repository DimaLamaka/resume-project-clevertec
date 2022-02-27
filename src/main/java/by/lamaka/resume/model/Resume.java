package by.lamaka.resume.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * Entity resume
 */
@Entity
@Table(name = "resumes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 2, message = "first name must be min 2 symbols")
    @NotBlank(message = "first name is required field")
    @Column(name = "first_name")
    String firstName;

    @Size(min = 2, message = "second name must be min 2 symbols")
    @NotBlank(message = "second name is required field")
    @Column(name = "second_name")
    String secondName;

    @Pattern(regexp = "(\\+375)-(29|33|44)-([0-9]{7})", message = "Please, use pattern for phone number +375-XX-XXXXXXX")
    @Column(name = "phone_number")
    String phoneNumber;

    @Pattern(regexp = "[A-Za-z0-9]+@(mail.ru|yandex.ru|gmail.com)",
            message = "please, use pattern ............@(mail.ru | yandex.ru | gmail.com)")
    @Column(name = "email")
    String email;

    @Past(message = "please, check your date")
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    Date dateOfBirth;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    Date dateOfCreation;

    @UpdateTimestamp
    Date dateOfUpdate;

    public Resume(Long id, String firstName, String secondName, String phoneNumber, String email, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
}
