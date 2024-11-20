package br.com.biroska.amazonintegrations.integration.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Contact")
public class ContactEntity {

    private String name;

    private String phone;

    public void updateFields(ContactEntity entity) {
        this.name = entity.getName();
        this.phone = entity.getPhone();
    }
}
