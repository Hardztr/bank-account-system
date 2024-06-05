package dk.bankaccountsystem.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ACCOUNT")
public class AccountEntity {

    public AccountEntity(String firstName, String lastName, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(
            generator = "uuid"
    )
    @GenericGenerator(
            name = "uuid",
            strategy = "uuid2"
    )
    @Column(
            name = "ACCOUNT_NUMBER",
            nullable = false,
            updatable = false
    )
    @Type(
            type = "uuid-char"
    )
    private UUID accountNumber;

    @Setter
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Setter
    @Column(name = "LAST_NAME")
    private String lastName;

    @Setter
    @Column(name = "BALANCE")
    private double balance;
}
