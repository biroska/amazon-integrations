package br.com.biroska.amazonintegrations.integration.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Person")
public class PersonEntity {

    @Id
    private String id;

    private String name;

    private String age;

    private String birthdate;

    private String gender;

    private List<ContactEntity> contacts;

    public void updateFields(PersonEntity entity) {
        this.name = entity.getName();
        this.age = entity.getAge();
        this.birthdate = entity.getBirthdate();
        this.gender = entity.getGender();
        this.contacts = entity.getContacts();
    }
}
