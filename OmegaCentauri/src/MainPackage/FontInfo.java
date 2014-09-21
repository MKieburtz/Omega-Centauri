package MainPackage;

import java.util.Objects;

/**
 * @author Michael Kieburtz
 */
class FontInfo {

    public String path;
    public Float size;

    public FontInfo(String path, Float size) {
        this.path = path;
        this.size = size;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof FontInfo)) {
            return false;
        } else {
            FontInfo info = (FontInfo) other;
            if (info.path.equals(this.path) && info.size.equals(size)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.path);
        hash = 23 * hash + Objects.hashCode(this.size);
        return hash;
    }
}
