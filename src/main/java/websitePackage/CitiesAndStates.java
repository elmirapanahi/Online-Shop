package websitePackage;

import java.util.Stack;

public class CitiesAndStates {
    public enum State{
        TEHRAN,
        ESFAHAN,KHORASANERAZAVI,KHORASANESHOMALI,KHORASANEJONOOBI,SISTANBALOOCHESTAN,KOHKILOOYEVABOYELAHMAD,
        FARS,KHOOZESTAN,ILAM,BOOSHER,KERMAN,YAZD,KORDESTAN,LORESTAN,ARDEBIL,AZARBAYEJANESHARGHI,
        AZARBAYEJANEGHARBI,QOM,MAZANDARAN,GILAN,KERMANSHAH,ZANJAN,GHAZVIN,BOJNOORD
    }

    public enum City{
        TEHRAN,ESLAMSHAHR,VARAMIN,DAMAVAND,SHARHRIYAR,PARAND,
        YAZD,
        ESFAHAN,
        MASHHAD,
        SARI,NASHTAROOD,BABOL,BABOLSAR,SALMANSHAHR,
        RASHT,
        SHIRAZ,
        QOM,
        KHORAMABAD,
        SANANDAJ,
        BIRGAND,
        AHVAZ,
        TABRIZ
    }

    public static State getStateOfCity(City city){
        switch (city){
            case TEHRAN,ESLAMSHAHR,VARAMIN,DAMAVAND,SHARHRIYAR,PARAND:
                return State.TEHRAN;
            case ESFAHAN:
                return State.ESFAHAN;
            case YAZD:
                return State.YAZD;
            case MASHHAD:
                return State.KHORASANERAZAVI;
            case SARI,NASHTAROOD,BABOL,BABOLSAR,SALMANSHAHR:
                return State.MAZANDARAN;
            case RASHT:
                return State.GILAN;
            case SHIRAZ:
                return State.FARS;
            case QOM:
                return State.QOM;
            case KHORAMABAD:
                return State.LORESTAN;
            case SANANDAJ:
                return State.KORDESTAN;
            case BIRGAND:
                return State.KHORASANEJONOOBI;
            case AHVAZ:
                return State.KHOOZESTAN;
            case TABRIZ:
                return State.AZARBAYEJANESHARGHI;
            default:
                return State.TEHRAN;

        }
    }
}
