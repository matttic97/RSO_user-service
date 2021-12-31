package user.lib;

import java.time.Instant;

public class InfoMetadata {


    private String[] clani = {"ji4607","dc9981","rl8622"};
    private String opis_projekta = "Problem, ki ga rešujemo je ta, da se študenti srečujejo s previsokimi cenami knjig, ki se jih priporoča ali celo zahteva imeti za posamezen predmet na fakulteti.\n" +
            "Kot rešitev, naš projekt implementira platformo za študente, ki omogoča prodajo ter nakup rabljenih knjig, po nižji ceni in omogočili deljenje zapiskov v elektronski obliki.";
    private String[] mikrostoritve = {"http://20.73.151.238:8080/v1/images", "http://20.71.68.192:8080/v1/notes", "http://20.73.35.91:8080/v1/baskets"};
    private String[] github = {"https://github.com/Skupina10/Images", "https://github.com/Skupina10/Notes","https://github.com/Skupina10/Literature", "https://github.com/Skupina10/Basket"};
    private String[] travis = {};
    private String[] dockerhub = {"https://hub.docker.com/repository/docker/rlikar/notes","https://hub.docker.com/repository/docker/rlikar/images",
            "https://hub.docker.com/repository/docker/rlikar/literature", "https://hub.docker.com/repository/docker/rlikar/basket"};



    public String[] getclani() {
        return clani;
    }

    public String getOpis_projekta() {
        return opis_projekta;
    }

    public String[] getMikrostoritve() {
        return mikrostoritve;
    }

    public String[] getGithub() {
        return github;
    }

    public String[] getTravis() {
        return travis;
    }

    public String[] getdockerhub() {
        return dockerhub;
    }

}
