package webapp.storage;

import webapp.model.Resume;

/** Test for your ArrayStorage implementation */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    //static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();
    Resume r1 = new Resume("uuid5");
    Resume r2 = new Resume("uuid6");
    Resume r3 = new Resume("uuid3");
    Resume r4 = new Resume("uuid4");

    public static void main(String[] args) {
        MainTestArrayStorage storage = new MainTestArrayStorage();
        storage.save();
        storage.get();
        storage.delete();
        storage.update();
        storage.clear();
        storage.emptyStorage();
    }

    public void save() {
        System.out.println("Fill in storage:");
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);
        System.out.println();

        System.out.println("Save null:");
        ARRAY_STORAGE.save(null);
        printAll();
        System.out.println();

        System.out.println("Save existent:");
        ARRAY_STORAGE.save(r1);
        System.out.println();

/*        System.out.println("Storage overflow:");
        for (int i = 0; i < ARRAY_STORAGE.size(); i++) {
            ARRAY_STORAGE.save(new Resume(String.valueOf(i)));
        }
        ARRAY_STORAGE.save(new Resume("test"));*/

        printLine();
    }

    public void get() {
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        printAll();
        System.out.println();

        printLine();
    }

    public void delete() {
        System.out.println("Delete " + r1.getUuid() + ":");
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        System.out.println();

        System.out.println("Delete non-existent:");
        ARRAY_STORAGE.delete("dummy");
        printAll();
        System.out.println();

        printLine();
    }

    public void update() {
        System.out.println("Update " + r2.getUuid() + ":");
        ARRAY_STORAGE.update(r2);
        printAll();
        System.out.println();

        System.out.println("Update non-existent:");
        ARRAY_STORAGE.update(new Resume("uuid5"));
        printAll();
        System.out.println();

        printLine();
    }

    public void clear() {
        System.out.println("Clear array:");
        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println();

        printLine();
    }

    public void emptyStorage() {
        ARRAY_STORAGE.get(r1.getUuid());
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.update(r1);
        ARRAY_STORAGE.clear();
        printAll();

        printLine();
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }

    static void printLine() {
        System.out.println("----------");
        System.out.println();
    }
}
