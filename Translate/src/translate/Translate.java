package translate;

/**
 *
 * @author Dang Minh Tuan SE150430
 */
public class Translate {

    private String VietNamese;
    private String English;

    public Translate(String VietNamese, String English) throws TranslateException {
        this.setVietNamese(VietNamese);
        this.setEnglish(English);
    }

    public String getVietNamese() {
        return VietNamese;
    }

    public void setVietNamese(String VietNamese) throws TranslateException {
        if (VietNamese.equals("")) {
            throw new TranslateException("Vietnamese can't be empty!");
        } else {
            this.VietNamese = VietNamese;
        }
    }

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String English) throws TranslateException {
        if (English.equals("")) {
            throw new TranslateException("English can't be empty!");
        } else {
            this.English = English;
        }
    }

}
