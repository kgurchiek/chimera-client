package dev.chimera.nemean;

import imgui.internal.ImGuiContext;

public class ImGui extends imgui.ImGui {
    private static boolean isInFrame = false;

    public static ImGuiContext createContext()
    {
        return imgui.ImGui.createContext();
    }

    public static void frame(Runnable code)
    {
        boolean hasStartedFrame = false;

        // Handle frame being called within another frame
        if (!isInFrame)
        {
            newFrame();
            hasStartedFrame = true;
        }

        code.run();

        if (hasStartedFrame)
        {
            render();
        }
    }

    public static void window(String name, Runnable code)
    {
        begin(name);
        code.run();
        end();
    }

    public static void newFrame()
    {
        if (isInFrame)
        {
            throw new RuntimeException("Attempted to create new frame despite frame already existing!");
        }
        isInFrame = true;
        imgui.ImGui.newFrame();
    }

    public static void endFrame()
    {
        if (!isInFrame)
        {
            throw new RuntimeException("Attempted to end frame outside of frame!");
        }
        isInFrame = false;
        imgui.ImGui.endFrame();
    }

    public static void render()
    {
        if (!isInFrame)
        {
            throw new RuntimeException("Attempted to end frame outside of frame!");
        }
        isInFrame = false;
        imgui.ImGui.render();
    }

    public static boolean begin(String text)
    {
        if (!isInFrame)
        {
            throw new RuntimeException("Attempted to create window outside of frame!");
        }
        return imgui.ImGui.begin(text);
    }
}
