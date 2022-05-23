package dungeonmania.entities.movingEntities;

public interface Subject {
    
    public void AddObserver(Observer observer);
    public void RemoveObserver(Observer observer);
    public void Notify();

}
