package model;

// TimeSignature is a time signature represented as top / bottom
public class TimeSignature {
    private int top;
    private int bot;

    public TimeSignature(int top, int bot) {
        this.top = top;
        this.bot = bot;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBot() {
        return bot;
    }

    public void setBot(int bot) {
        this.bot = bot;
    }
}
