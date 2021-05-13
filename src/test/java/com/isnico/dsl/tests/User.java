package com.isnico.dsl.tests;

import com.isnico.dsl.Context;
import com.isnico.dsl.Dsl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String name;

}
