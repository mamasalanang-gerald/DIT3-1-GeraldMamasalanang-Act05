# NoteKeeperApp – Activity 05

**Name:** Gerald Mamasalanang  
**Section:** DIT 3-1  
**Activity Title:** SQLite CRUD and Data Persistence  
**Repository Name:** DIT3-1-GeraldMamasalanang-Act05  

---

## Project Description
This project is an Android app called **NoteKeeperApp** that allows users to create, view, edit, and delete notes using an **SQLite database**.  
The app demonstrates data persistence, meaning all notes remain available even after closing and reopening the app.  

The main features include:
- A **RecyclerView** that lists all saved notes  
- A **Floating Action Button (FAB)** to add new notes  
- Full **CRUD** functionality (Create, Read, Update, Delete)  
- An **SQLiteOpenHelper** class to manage database creation and versioning  

---

## Reflection

**1. How did you implement CRUD using SQLite?**  
I created a helper class that extends `SQLiteOpenHelper` to handle the database. Inside it, I defined a `notes` table with columns for `id`, `title`, `content`, and `timestamp`. I used SQLite queries for each operation — `insert()` for creating notes, `query()` for reading, `update()` for editing, and `delete()` for removing notes. The results were displayed using a **RecyclerView**.

**2. What challenges did you face in maintaining data persistence?**  
The main challenge was making sure the RecyclerView updates correctly after adding or editing a note. I also had to make sure the database connection closed properly to avoid memory leaks. Debugging the refresh logic after performing CRUD operations was also a bit tricky.

**3. How could you improve performance or UI design in future versions?**  
In future versions, I’d like to add features like note searching, sorting by date, and using **Room Database** for better performance and cleaner code. For the UI, I would improve the layout with better spacing, modern colors, and maybe add animations when notes are added or deleted.

---

## How to Run
1. Clone this repository:
   ```bash
   git clone git@github.com:mamasalanang-gerald/DIT3-1-GeraldMamasalanang-Act05.git
