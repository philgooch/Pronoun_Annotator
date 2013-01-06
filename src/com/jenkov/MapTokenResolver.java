/**
 * From Jakob Jenkov
 * http://tutorials.jenkov.com/java-howto/replace-strings-in-streams-arrays-files.html
 */

package com.jenkov;

import java.util.*;

public class MapTokenResolver implements ITokenResolver {

  protected Map<String, String> tokenMap = new HashMap<String, String>();

  public MapTokenResolver(Map<String, String> tokenMap) {
    this.tokenMap = tokenMap;
  }

  public String resolveToken(String tokenName) {
    return this.tokenMap.get(tokenName);
  }

}
