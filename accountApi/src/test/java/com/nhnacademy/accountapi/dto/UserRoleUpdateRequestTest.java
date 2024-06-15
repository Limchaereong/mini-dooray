package com.nhnacademy.accountapi.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

class UserRoleUpdateRequestTest {

    @Test
    void testUserRoleUpdateRequestConstructor() {
        // given
        List<String> emails = Arrays.asList("test1@example.com", "test2@example.com");
        String role = "admin";

        // when
        UserRoleUpdateRequest userRoleUpdateRequest = new UserRoleUpdateRequest(emails, role);

        // then
        assertThat(userRoleUpdateRequest.emails()).isEqualTo(emails);
        assertThat(userRoleUpdateRequest.role()).isEqualTo(role);
    }

    @Test
    void testUserRoleUpdateRequestBuilder() {
        // given
        List<String> emails = Arrays.asList("test1@example.com", "test2@example.com");
        String role = "admin";

        // when
        UserRoleUpdateRequest userRoleUpdateRequest = UserRoleUpdateRequest.builder()
                .emails(emails)
                .role(role)
                .build();

        // then
        assertThat(userRoleUpdateRequest.emails()).isEqualTo(emails);
        assertThat(userRoleUpdateRequest.role()).isEqualTo(role);
    }

    @Test
    void testUserRoleUpdateRequestToString() {
        // given
        List<String> emails = Arrays.asList("test1@example.com", "test2@example.com");
        String role = "admin";

        // when
        UserRoleUpdateRequest userRoleUpdateRequest = new UserRoleUpdateRequest(emails, role);
        String expectedString = "UserRoleUpdateRequest[emails=[test1@example.com, test2@example.com], role=admin]";

        // then
        assertThat(userRoleUpdateRequest.toString()).isEqualTo(expectedString);
    }

    @Test
    void testUserRoleUpdateRequestEqualsAndHashCode() {
        // given
        List<String> emails1 = Arrays.asList("test1@example.com", "test2@example.com");
        List<String> emails2 = Arrays.asList("test1@example.com", "test2@example.com");
        String role1 = "admin";
        String role2 = "admin";

        UserRoleUpdateRequest userRoleUpdateRequest1 = new UserRoleUpdateRequest(emails1, role1);
        UserRoleUpdateRequest userRoleUpdateRequest2 = new UserRoleUpdateRequest(emails2, role2);

        // then
        assertThat(userRoleUpdateRequest1).isEqualTo(userRoleUpdateRequest2);
        assertThat(userRoleUpdateRequest1.hashCode()).isEqualTo(userRoleUpdateRequest2.hashCode());
    }
}
