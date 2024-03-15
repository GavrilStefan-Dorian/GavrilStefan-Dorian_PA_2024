import java.time.LocalTime;
import java.util.Objects;

/**
 * Client class
 */
public class Client {
    private ClientType type;

    private String name;
    private LocalTime minTime;
    private LocalTime maxTime;
    public Client() { }
    public Client(String name) {
        this(name, null, null);
    }
    public Client(String name, LocalTime minTime, LocalTime maxTime) {
        this.name = name;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public Client(String name, ClientType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public LocalTime getMinTime() {
        return minTime;
    }

    public LocalTime getMaxTime() {
        return maxTime;
    }

    public String getType() {
        switch(type) {
            case REGULAR -> {
                return "Regular";
            }
            case PREMIUM -> {
                return "Premium";
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinTime(LocalTime minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(LocalTime maxTime) {
        this.maxTime = maxTime;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Client{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return type == client.type && Objects.equals(name, client.name) && Objects.equals(minTime, client.minTime) && Objects.equals(maxTime, client.maxTime);
    }
}
