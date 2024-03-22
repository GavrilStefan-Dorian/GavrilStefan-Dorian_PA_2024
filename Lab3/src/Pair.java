import java.util.Objects;

public class Pair<T, U> {
    protected T start;
    protected U end;
    public Pair(T start, U end) {
        this.start = start;
        this.end = end;
    }

    public T getFirst() {
        return start;
    }

    public U getSecond() {
        return end;
    }

    public void setFirst(T open) {
        this.start = open;
    }

    public void setSecond(U closed) {
        this.end = closed;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + start +
                ", second=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(start, pair.start) && Objects.equals(end, pair.end);
    }
}





























