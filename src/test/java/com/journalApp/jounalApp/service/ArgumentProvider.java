package com.journalApp.jounalApp.service;

import com.journalApp.jounalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class ArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Arguments.of(User.builder().userName("Abhishek").password("Abhishek10452").build()),
                Arguments.of(User.builder().userName("Aarush").password("").build())
                );
    }
}
