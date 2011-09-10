package br.com.xisp.validations;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CustomValidations {

    private static TypeSafeMatcher<String> EMPTY = new TypeSafeMatcher<String>() {

        protected void describeMismatchSafely(String item, Description mismatchDescription) {
            mismatchDescription.appendText(" " + item);
        }

        public boolean matchesSafely(String item) {
            return item != null && !item.equals("");
        }

        public void describeTo(Description description) {
            description.appendText(" not empty");
        }

    };

    /**
* matches if the given string is not empty. This matcher is null-safe.
* @return
*/
    public static TypeSafeMatcher<String> notEmpty() {
        return EMPTY;
    }

}