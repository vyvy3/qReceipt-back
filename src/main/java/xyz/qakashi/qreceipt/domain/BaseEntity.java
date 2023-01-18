package xyz.qakashi.qreceipt.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity <T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;
}
