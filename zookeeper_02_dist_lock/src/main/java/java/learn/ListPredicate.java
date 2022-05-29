package java.learn;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListPredicate {
    public void testPredicate() {
        final String protectedPrefix = "_c-lock";

        List<String> list = new ArrayList<>();
        String foundNode = Iterables.find
            (
                    list,
                    new Predicate<String>() {
                        @Override
                        public boolean apply(String node) {
                            return node.startsWith(protectedPrefix);
                        }
                    },
                    null
            );

        String foundElement = list.stream().filter(new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String node) {
                return node.startsWith(protectedPrefix);
            }
        }).findFirst().orElse(null);
    }
}
