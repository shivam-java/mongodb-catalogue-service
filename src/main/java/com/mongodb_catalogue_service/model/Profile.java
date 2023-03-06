package com.mongodb_catalogue_service.model;



import lombok.*;


import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Profile {

    private   String name;
    String country;
    String birthPlace;
    int age;
    String role;
    List<String> teams;
}
