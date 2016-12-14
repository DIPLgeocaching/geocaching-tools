package org.geocachingtools.geoui;

import java.awt.image.BufferedImage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.geocachingtools.decoder.Decoder;
import org.geocachingtools.decoder.DecoderMethod;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;

@ManagedBean
@SessionScoped
public class HelloBean implements Serializable {

    private Map<DecoderMethod, Boolean> map = new HashMap<>();
    private Map<DecoderMethod, String> results = new HashMap<>();
    private Map<DecoderMethod, String> briefs = new HashMap<>();
    private String inputText = "";
    private String passwords = "";

    public Map<DecoderMethod, Boolean> getMap() {
        return map;
    }

    public Map<DecoderMethod, String> getResults() {
        return results;
    }

    public Map<DecoderMethod, String> getBriefs() {
        return briefs;
    }

    public String getInputText() {
        return inputText;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getName() {
        Decoder decoder = Decoder.getInstance();
        return decoder.getMethods(String.class).stream()
                .map(method -> String.format("%s requires password:%s  process:%s", method.getName(), Boolean.toString(method.getRequiresPassword()), method.getType().toString()))
                .reduce((a, b) -> a + "<br>" + b)
                .orElse("Keine String Methoden vorhanden <br>")
                + decoder.getMethods(BufferedImage.class).stream()
                        .map(method -> String.format("%s requires password:%s  process:%s", method.getName(), Boolean.toString(method.getRequiresPassword()), method.getType().toString()))
                        .reduce((a, b) -> a + "<br>" + b)
                        .orElse("Keine Image Methoden vorhanden<br>");
    }

    public Collection<DecoderMethod> getMethods() {
        return Decoder.getInstance().getMethods(String.class);
    }

    public void doit() {
        System.out.println(map);
        results.clear();
        for (Map.Entry<DecoderMethod, Boolean> entry : map.entrySet()) {
            if (entry.getValue()) {
                //baue request zusammen
                DecoderRequest<String> request = new DecoderRequest<String>(
                        String.class,
                        inputText,
                        entry.getKey(),
                        Arrays.asList(passwords.split("\n"))
                );
                //schicke request synchron ab
                DecoderResult result = entry.getKey().decode(request);
                results.put(entry.getKey(), result.getFullResult());
                briefs.put(entry.getKey(), result.getBriefResult());
            }
        }
    }
}
