package com.geocachetools;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JLanguageTool langTool = new JLanguageTool(new BritishEnglish());
// comment in to use statistical ngram data:
//langTool.activateLanguageModelRules(new File("/data/google-ngram-data"));
        List<RuleMatch> matches = null;
        try {
            matches = langTool.check("A sentence with a error in the Hitchhiker's Guide tot he Galaxy");

            for (RuleMatch match : matches) {
                System.out.println("Potential error at characters " +
                        match.getFromPos() + "-" + match.getToPos() + ": " +
                        match.getMessage());
                System.out.println("Suggested correction(s): " +
                        match.getSuggestedReplacements());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
