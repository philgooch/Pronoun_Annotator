/**
 * From Jakob Jenkov
 * http://tutorials.jenkov.com/java-howto/replace-strings-in-streams-arrays-files.html
 */


package com.jenkov;

public interface ITokenResolver {

    public String resolveToken(String tokenName);
}
