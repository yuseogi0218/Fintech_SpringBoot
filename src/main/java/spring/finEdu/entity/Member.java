package spring.finEdu.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Member {
     @Id
     @GeneratedValue
     Long seq;
     String id;
     String name;
     String org;
     Boolean active;
}