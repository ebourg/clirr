package net.sf.clirr.checks;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import net.sf.clirr.framework.ApiDiffDispatcher;
import net.sf.clirr.event.ApiDifference;
import net.sf.clirr.event.MessageTranslator;
import junit.framework.TestCase;

class TestDiffListener implements ApiDiffDispatcher
{
        private MessageTranslator translator = new MessageTranslator();
        
        private Set diffs = new HashSet();

        public void fireDiff(ApiDifference difference)
        {
            diffs.add(difference);
        }

        public void checkExpected(ExpectedDiff[] expectedDiffs)
        {
            for (int i=0; i<expectedDiffs.length; ++i)
            {
                ExpectedDiff expected = expectedDiffs[i];
                
                // now see if the expected diff is in fact in the set of
                // diffs that occurred during the test comparison
                boolean found = false;
                for(Iterator j = diffs.iterator(); j.hasNext() && !found;)
                {
                    ApiDifference actual = (ApiDifference) j.next();
                    found = expected.matches(actual);
                }
                
                if (!found)
                {
                    TestCase.fail("expected diff " + expected + " was not generated: " + diffs);
                }
            }

            for (Iterator it = diffs.iterator(); it.hasNext();) {
                ApiDifference actual = (ApiDifference) it.next();
                
                // see if the generated diff is in fact in the expected set
                boolean found = false;
                for(int i=0; i<expectedDiffs.length && !found; ++i)
                {
                    found = expectedDiffs[i].matches(actual);
                }
                
                if (!found)
                {
                    TestCase.fail("unexpected diff " + actual);
                }
            }
        }
}
