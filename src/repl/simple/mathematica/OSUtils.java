package repl.simple.mathematica;

/**
 * Created by alex on 3/6/14.
 */
/**
 * helper class to check the operating system this Java VM runs in
 * http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
 * compare to http://svn.terracotta.org/svn/tc/dso/tags/2.6.4/code/base/common/src/com/tc/util/runtime/Os.java
 * http://www.docjar.com/html/api/org/apache/commons/lang/SystemUtils.java.html
 */
public final class OSUtils {
    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows, MacOSx32, MacOSx64, Linux, Linux64, Other
    };

    protected static OSType detectedOS;

    /**
     * detected the operating system from the os.name System property and cache
     * the result
     *
     * @returns - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase();
            if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
                String ARCH = System.getProperty("os.arch","generic").toLowerCase();
                detectedOS = OSType.MacOSx32;
                if(ARCH.indexOf("64") >=0 )
                {
                    detectedOS = OSType.MacOSx64;
                }
            } else if (OS.indexOf("win") >= 0) {
                detectedOS = OSType.Windows;
            } else if (OS.indexOf("nux") >= 0) {
                String ARCH = System.getProperty("os.arch","generic").toLowerCase();
                detectedOS = OSType.Linux;
                if(ARCH.indexOf("64") >=0 ) {
                    detectedOS = OSType.Linux64;
                }
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
}