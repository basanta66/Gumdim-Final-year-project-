package com.aoh.ghumdim.shared;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
//@MappedSuperclass
public abstract class Timestampt {



    @CreationTimestamp
    private LocalDateTime dateTimeAtCreation;

    @UpdateTimestamp
    private LocalDateTime updateTimestamp;


}
