package gg.mafiya.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MafiyaScreen extends Screen {
    private static final String MESSAGE = "Hack kduy mea hg s'ey?";

    private final Screen parent;

    public MafiyaScreen(Screen parent) {
        super(Text.literal("Mafiya Client"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int panelH = (int) (this.height * 0.6f);
        int bottom = (this.height + panelH) / 2;
        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> this.close())
                .dimensions(this.width / 2 - 60, bottom - 30, 120, 20)
                .build());
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);

        int panelW = (int) (this.width * 0.8f);
        int panelH = (int) (this.height * 0.6f);
        int x0 = (this.width - panelW) / 2;
        int y0 = (this.height - panelH) / 2;

        context.fill(x0 - 2, y0 - 2, x0 + panelW + 2, y0 + panelH + 2, 0xFFFF2020);
        context.fill(x0, y0, x0 + panelW, y0 + panelH, 0xFF0B0B0F);

        context.drawTextWithShadow(this.textRenderer, Text.literal("MAFIYA CLIENT"), x0 + 8, y0 + 8, 0xFFFF4040);

        String by = "by MafiyaGooner";
        context.drawTextWithShadow(this.textRenderer, Text.literal(by),
                x0 + panelW - 8 - this.textRenderer.getWidth(by), y0 + panelH - 16, 0xFF888888);

        float scale = Math.min(5.0f, (panelW - 30.0f) / this.textRenderer.getWidth(MESSAGE));
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(this.width / 2.0f, this.height / 2.0f - (this.textRenderer.fontHeight * scale) / 2.0f - 10.0f);
        context.getMatrices().scale(scale, scale);
        context.drawTextWithShadow(this.textRenderer, Text.literal(MESSAGE),
                -this.textRenderer.getWidth(MESSAGE) / 2, 0, 0xFFFF3030);
        context.getMatrices().popMatrix();
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
}
