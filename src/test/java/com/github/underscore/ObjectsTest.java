/*
 * The MIT License (MIT)
 *
 * Copyright 2015-2018 Valentyn Kolesnikov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.underscore;

import java.util.*;
import org.junit.Test;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Underscore library unit test.
 *
 * @author Valentyn Kolesnikov
 */
public class ObjectsTest {

/*
_.keys({one: 1, two: 2, three: 3});
=> ["one", "two", "three"]
*/
    @Test
    public void keys() {
        Set<String> result = $.keys(new LinkedHashMap<String, Object>() { {
            put("one", 1); put("two", 2); put("three", 3); } });
        assertEquals("[one, two, three]", result.toString());
    }

/*
_.values({one: 1, two: 2, three: 3});
=> [1, 2, 3]
*/
    @Test
    public void values() {
        Collection<Integer> result = $.values(new LinkedHashMap<String, Integer>() { {
            put("one", 1); put("two", 2); put("three", 3); } });
        assertEquals("[1, 2, 3]", result.toString());
    }

/*
_.pairs({one: 1, two: 2, three: 3});
=> [["one", 1], ["two", 2], ["three", 3]]
*/
    @Test
    public void pairs() {
        List<Tuple<String, Integer>> result = $.pairs(new LinkedHashMap<String, Integer>() { {
            put("one", 1); put("two", 2); put("three", 3); } });
        assertEquals("[(one, 1), (two, 2), (three, 3)]", result.toString());
    }

/*
_.invert({Moe: "Moses", Larry: "Louis", Curly: "Jerome"});
=> {Moses: "Moe", Louis: "Larry", Jerome: "Curly"};
*/
    @Test
    public void invert() {
        List<Tuple<String, String>> result = $.invert(new LinkedHashMap<String, String>() { {
            put("Moe", "Moses"); put("Larry", "Louis"); put("Curly", "Jerome"); } });
        assertEquals("[(Moses, Moe), (Louis, Larry), (Jerome, Curly)]", result.toString());
    }

/*
_.functions(_);
=> ["all", "any", "bind", "bindAll", "clone", "compact", "compose" ...
*/
    @Test
    public void functions() {
        List<String> result = $.functions($.class);
        assertEquals(5, $.first(result, 5).size());
    }

/*
_.methods(_);
=> ["all", "any", "bind", "bindAll", "clone", "compact", "compose" ...
*/
    @Test
    public void methods() {
        List<String> result = $.methods($.class);
        assertEquals(5, $.first(result, 5).size());
    }

/*
_.pick({name: 'moe', age: 50, userid: 'moe1'}, 'name', 'age');
=> {name: 'moe', age: 50}
_.pick({name: 'moe', age: 50, userid: 'moe1'}, function(value, key, object) {
  return _.isNumber(value);
});
=> {age: 50}
*/
    @Test
    public void pick() {
        final List<Tuple<String, Object>> result = $.pick(
            new LinkedHashMap<String, Object>() { { put("name", "moe"); put("age", 50); put("userid", "moe1"); } },
            "name", "age"
        );
        assertEquals("[(name, moe), (age, 50)]", result.toString());
        final List<Tuple<String, Object>> result2 = $.pick(
            new LinkedHashMap<String, Object>() { { put("name", "moe"); put("age", 50); put("userid", "moe1"); } },
            new Predicate<Object>() { public boolean test(Object value) {
                return value instanceof Number; } }
        );
        assertEquals("[(age, 50)]", result2.toString());
    }

/*
_.omit({name: 'moe', age: 50, userid: 'moe1'}, 'userid');
=> {name: 'moe', age: 50}
_.omit({name: 'moe', age: 50, userid: 'moe1'}, function(value, key, object) {
  return _.isNumber(value);
});
=> {name: 'moe', userid: 'moe1'}
*/
    @Test
    public void omit() {
        final List<Tuple<String, Object>> result = $.omit(
            new LinkedHashMap<String, Object>() { { put("name", "moe"); put("age", 50); put("userid", "moe1"); } },
            "userid"
        );
        assertEquals("[(name, moe), (age, 50)]", result.toString());
        final List<Tuple<String, Object>> result2 = $.omit(
            new LinkedHashMap<String, Object>() { { put("name", "moe"); put("age", 50); put("userid", "moe1"); } },
            new Predicate<Object>() { public boolean test(Object value) {
                return value instanceof Number; } }
        );
        assertEquals("[(name, moe), (userid, moe1)]", result2.toString());
    }

/*
var iceCream = {flavor: "chocolate"};
_.defaults(iceCream, {flavor: "vanilla", sprinkles: "lots"});
=> {flavor: "chocolate", sprinkles: "lots"}
*/
    @Test
    public void defaults() {
        Map<String, String> iceCream = new LinkedHashMap<String, String>() { { put("flavor", "chocolate"); } };
        Map<String, String> result = $.defaults(iceCream, new LinkedHashMap<String, String>() { {
            put("flavor", "vanilla"); put("sprinkles", "lots"); } });
        assertEquals("{flavor=chocolate, sprinkles=lots}", result.toString());
    }

/*
_.clone({name: 'moe'});
=> {name: 'moe'};
*/
    @Test
    @SuppressWarnings("unchecked")
    public void cloneMap() {
        Map<String, String> result = (Map<String, String>) $.clone(new LinkedHashMap<String, String>() { {
            put("name", "moe"); } });
        assertEquals("{name=moe}", result.toString());
        Integer[] result2 = $.clone(new Integer[] { 1, 2, 3, 4, 5 });
        assertEquals("[1, 2, 3, 4, 5]", asList(result2).toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cloneError() {
        class Test {
        }
        $.clone(new Test());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cloneError2() {
        class Test implements Cloneable {
            public Object clone(String arg) {
                return null; }
        }
        $.clone(new Test());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cloneError3() {
        class Test implements Cloneable {
            public Object clone() throws CloneNotSupportedException {
                super.clone(); throw new RuntimeException(); }
        }
        $.clone(new Test());
    }

/*
var stooge = {name: 'moe', luckyNumbers: [13, 27, 34]};
var clone  = {name: 'moe', luckyNumbers: [13, 27, 34]};
stooge == clone;
=> false
_.isEqual(stooge, clone);
=> true
_.isEqual(null, null)
=> true
_.isEqual('Curly', 'Curly')
=> true
*/
    @Test
    public void isEqual() {
        Map<String, Object> stooge = new LinkedHashMap<String, Object>() { {
            put("name", "moe"); put("luckyNumbers", asList(13, 27, 34)); } };
        Map<String, Object> clone = new LinkedHashMap<String, Object>() { {
            put("name", "moe"); put("luckyNumbers", asList(13, 27, 34)); } };
        assertFalse(stooge == clone);
        assertTrue($.isEqual(stooge, clone));
        assertTrue($.isEqual(null, null));
        assertFalse($.isEqual(stooge, null));
        assertFalse($.isEqual(null, clone));
        assertTrue($.isEqual("Curly", "Curly"));
        assertTrue($.isEqual(0, -0));
        assertTrue($.isEqual(75, 75));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void isEmpty() {
        assertTrue($.isEmpty((List) null));
        assertTrue($.isEmpty(new ArrayList<String>()));
        assertTrue(new $((List) null).isEmpty());
        assertTrue(new $(new ArrayList<String>()).isEmpty());
        assertTrue($.chain((List) null).isEmpty());
        assertTrue($.chain(new ArrayList<String>()).isEmpty());
        assertFalse($.isEmpty(asList("")));
        assertFalse(new $(asList("")).isEmpty());
        assertFalse($.chain(asList("")).isEmpty());
        assertTrue($.isEmpty((Map) null));
        assertTrue($.isEmpty(new HashMap<String, String>()));
        assertFalse($.isEmpty(new HashMap<String, String>() { { put("", ""); } }));
    }
/*
_.isObject({});
=> true
_.isObject(1);
=> false
*/
    @Test
    public void isObject() {
        assertTrue($.isObject(new LinkedHashMap<String, String>()));
        assertFalse($.isObject(null));
        assertFalse($.isObject("string"));
    }

/*
_.isArray([1,2,3]);
=> true
*/
    @Test
    public void isArray() {
        assertTrue($.isArray(new int[] {1, 2, 3, 4, 5}));
        assertFalse($.isArray(null));
        assertFalse($.isArray("string"));
    }

/*
_.isString("moe");
=> true
*/
    @Test
    public void isString() {
        assertTrue($.isString("moe"));
    }

/*
_.isNumber(8.4 * 5);
=> true
*/
    @Test
    public void isNumber() {
        assertTrue($.isNumber(8.4 * 5));
    }

/*
_.isBoolean(null);
=> false
*/
    @Test
    public void isBoolean() {
        assertTrue($.isBoolean(false));
        assertFalse($.isBoolean(null));
    }

/*
_.isFunction(alert);
=> true
*/
    @Test
    public void isFunction() {
        assertTrue($.isFunction(new Function<String, Integer>() {
            public Integer apply(final String arg) {
                return null; } }));
    }

/*
_.isDate(new Date());
=> true
*/
    @Test
    public void isDate() {
        assertTrue($.isDate(new java.util.Date()));
        assertFalse($.isDate(null));
    }

/*
_.isRegExp(/moe/);
=> true
*/
    @Test
    public void isRegExp() {
        assertTrue($.isRegExp(java.util.regex.Pattern.compile("moe")));
        assertFalse($.isRegExp(null));
    }

/*
_.isNull(null);
=> true
*/
    @Test
    public void isNull() {
        assertTrue($.isNull(null));
        assertFalse($.isNull(""));
    }

/*
try {
  throw new TypeError("Example");
} catch (o_O) {
  _.isError(o_O)
}
=> true
*/
    @Test
    public void isError() {
        assertTrue($.isError(new Exception()));
        assertFalse($.isError(null));
    }

/*
_.chain([1,2,3,200])
  .filter(function(num) { return num % 2 == 0; })
  .tap(alert)
  .map(function(num) { return num * num })
  .value();
=> // [2, 200] (alerted)
=> [4, 40000]
*/
    @Test
    @SuppressWarnings("unchecked")
    public void tap() {
        final List<Map.Entry<String, Integer>> result = new ArrayList<Map.Entry<String, Integer>>();
        $.tap((new LinkedHashMap<String, Integer>() { { put("a", 1); put("b", 2); put("c", 3); } }).entrySet(),
            new Consumer<Map.Entry<String, Integer>>() {
                public void accept(final Map.Entry<String, Integer> item) {
                    result.add(item);
                }
            });
        assertEquals("[a=1, b=2, c=3]", result.toString());
        final List<Map.Entry<String, Integer>> resultChain = new ArrayList<Map.Entry<String, Integer>>();
        $.chain((new LinkedHashMap<String, Integer>() { { put("a", 1); put("b", 2); put("c", 3); } }).entrySet())
            .tap(new Consumer<Map.Entry<String, Integer>>() {
                public void accept(final Map.Entry<String, Integer> item) {
                    resultChain.add(item);
                }
            });
        assertEquals("[a=1, b=2, c=3]", resultChain.toString());
    }

/*
_.has({a: 1, b: 2, c: 3}, "b");
=> true
*/
    @Test
    public void has() {
        boolean result = $.has(new LinkedHashMap<String, Integer>() { {
            put("a", 1); put("b", 2); put("c", 3); } }, "b");
        assertTrue(result);
    }

/*
var stooge = {name: 'moe', age: 32};
_.isMatch(stooge, {age: 32});
=> true
*/
    @Test
    public void isMatch() {
        Map<String, Object> stooge = new LinkedHashMap<String, Object>() { { put("name", "moe"); put("age", 32); } };
        assertTrue($.isMatch(stooge, new LinkedHashMap<String, Object>() { { put("age", 32); } }));
        Map<String, Object> stooge2 = new LinkedHashMap<String, Object>() { { put("name", "moe"); put("age", 33); } };
        assertFalse($.isMatch(stooge2, new LinkedHashMap<String, Object>() { { put("age", 32); } }));
        Map<String, Object> stooge3 = new LinkedHashMap<String, Object>() { { put("name", "moe"); } };
        assertFalse($.isMatch(stooge3, new LinkedHashMap<String, Object>() { { put("age", 32); } }));
    }

/*
var ready = _.matcher({selected: true, visible: true});
var readyToGoList = _.filter(list, ready);
*/
    @Test
    @SuppressWarnings("unchecked")
    public void matcher() {
        List<Map<String, Object>> list = Arrays.<Map<String, Object>>asList(
            new LinkedHashMap<String, Object>() { {
                put("name", "moe"); put("selected", true); put("visible", true); } },
            new LinkedHashMap<String, Object>() { {
                put("name", "larry"); put("selected", true); put("visible", false); } },
            new LinkedHashMap<String, Object>() { {
                put("name", "curly"); } }
        );
        Predicate<Map<String, Object>> ready = $.matcher(new LinkedHashMap<String, Object>() { {
            put("selected", true); put("visible", true); } });
        List<Map<String, Object>> result = $.filter(list, ready);
        assertEquals("[{name=moe, selected=true, visible=true}]", result.toString());
    }

/*
_.findKey([1, 2, 3], function(item) {return item % 2  === 0; });
=> 2
*/
    @Test
    public void findKey() {
        final Integer result = $.findKey(asList(1, 2, 3), new Predicate<Integer>() {
            public boolean test(Integer item) {
                return item % 2 == 0;
            }
        });
        assertEquals(2, result.intValue());
        final Integer resultNotFound = $.findKey(asList(1, 2, 3), new Predicate<Integer>() {
            public boolean test(Integer item) {
                return item > 3;
            }
        });
        assertNull(resultNotFound);
        final Integer resultArray = $.findKey(new Integer[] {1, 2, 3}, new Predicate<Integer>() {
            public boolean test(Integer item) {
                return item % 2 == 0;
            }
        });
        assertEquals(2, resultArray.intValue());
    }

/*
_.findLastKey([1, 2, 3, 4, 5], function(item) {return item % 2  === 0; });
=> 4
*/
    @Test
    public void findLastKey() {
        final Integer result = $.findLastKey(asList(1, 2, 3, 4, 5), new Predicate<Integer>() {
            public boolean test(Integer item) {
                return item % 2 == 0;
            }
        });
        assertEquals(4, result.intValue());
        final Integer resultNotFound = $.findLastKey(asList(1, 2, 3, 4, 5), new Predicate<Integer>() {
            public boolean test(Integer item) {
                return item > 5;
            }
        });
        assertNull(resultNotFound);
        final Integer resultArray = $.findLastKey(new Integer[] {1, 2, 3, 4, 5}, new Predicate<Integer>() {
            public boolean test(Integer item) {
                return item % 2 == 0;
            }
        });
        assertEquals(4, resultArray.intValue());
    }

/*
_.extend({name: 'moe'}, {age: 50});
=> {name: 'moe', age: 50}
*/
    @Test
    @SuppressWarnings("unchecked")
    public void extend() {
        assertEquals("{name=moe, age=50}", $.extend(new LinkedHashMap<String, Object>() { { put("name", "moe"); } },
            new LinkedHashMap<String, Object>() { { put("age", 50); } }).toString());
    }

/*
_.mapObject({start: 5, end: 12}, function(val, key) {
  return val + 5;
});
=> {start: 10, end: 17}
*/
    @Test
    public void mapObject() {
        List<Tuple<String, Integer>> result = $.mapObject(new LinkedHashMap<String, Integer>() { {
            put("start", 5); put("end", 12); } }, new Function<Integer, Integer>() {
            public Integer apply(Integer item) {
                return item + 5;
            }
        });
        assertEquals("[(start, 10), (end, 17)]", result.toString());
    }

}
