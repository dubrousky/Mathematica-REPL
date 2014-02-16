/**
 * Created by alex on 2/5/14.
 */
public class MathSettings {
    private static MathSettings ourInstance = new MathSettings();

    public static MathSettings getInstance() {
        return ourInstance;
    }

    private MathSettings() {
    }

    private String mathKernelPath;
    private String nativeLibraryPath;
    private String mathLinkPath;

    public String getMathKernelPath() {
        return mathKernelPath;
    }

    public void setMathKernelPath(String mathKernelPath) {
        this.mathKernelPath = mathKernelPath;
    }

    public String getNativeLibraryPath() {
        return nativeLibraryPath;
    }

    public void setNativeLibraryPath(String nativeLibraryPath) {
        this.nativeLibraryPath = nativeLibraryPath;
    }

    public String getMathLinkPath() {
        return mathLinkPath;
    }

    public void setMathLinkPath(String mathLinkPath) {
        this.mathLinkPath = mathLinkPath;
    }
}
