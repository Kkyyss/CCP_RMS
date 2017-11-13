/*
 * Assistant
 */
package rms.Model;

public class Assistant extends Staff {
    public static Assistant assistant = new Assistant();
    
    public Assistant() {
        super(JobPosition.Position.ASSISTANT);
    }
}
