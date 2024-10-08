package br.com.biroska.amazonintegrations.person.model;

import java.time.LocalDate;
import java.util.List;

public record Person(String id, String name, int age, LocalDate birthdate, String gender, List<Contact> contacts) {
}
