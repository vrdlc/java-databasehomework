import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private String description;

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Task(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object otherTask) {
    if(!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }

    public static List<Task> all() {
      String sql = "SELECT id, description FROM Tasks";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Task.class);
      }
    }
    public void save() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO Tasks (description) VALUES (:description)";
        con.createQuery(sql) //this is a return to break up single statement
          .addParameter("description", this.description) //so is this
          .executeUpdate() //and this
          .getKey();
      }
    }

    public static Task find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM Tasks where id=:id";
        Task task = con.createQuery(sql) //and this
          .addParameter("id", id) //and this
          .executeAndFetchFirst(Task.class);
      }
    }
  }
