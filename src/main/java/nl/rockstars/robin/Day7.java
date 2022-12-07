package nl.rockstars.robin;

import java.util.*;

public class Day7 implements DayProcessor {

    private static final long available = 70000000L;

    private static final long needed = 30000000L;

    private final Dir root;
    private Dir currentDir;

    public Day7() {
        root = Dir.root();
        currentDir = root;
    }

    public static void main(String[] args) {
        new Day7().go();
    }

    @Override
    public void process(String line) {
        if (line.startsWith("$")) {
            runCmd(line.substring(2));
        } else if (line.startsWith("dir")) {
            addNewDir(Dir.of(line.substring(4), currentDir));
        } else {
            var f = line.split(" ");
            addNewFile(File.of(Long.valueOf(f[0]), f[1]));
        }
    }

    private void addNewFile(File file) {
        currentDir.add(file);
    }

    private void addNewDir(Dir dir) {
        currentDir.add(dir);
    }

    @Override
    public Result getResult() {
        var required = Math.max(0, needed - (available - root.size()));
        var sum = root.subdirs().stream().mapToLong(Dir::size).filter(i -> i >= required).min().orElse(0L);
        return new Result((int) sum);
    }

    void runCmd(String cmd) {
        if (cmd.startsWith("cd")) {
            var name = cmd.split(" ")[1];
            if (name.equals("/")) {
                currentDir = root;
            } else if (name.equals("..")) {
                if (currentDir == root)
                    throw new IllegalStateException("cannot go up in root");
                currentDir = currentDir.parent();
            } else {
                currentDir = currentDir.resolve(name);
            }
        }
    }

    static final class Dir {
        private final String name;
        private final Dir parent;
        private final List<File> files;
        private final Map<String, Dir> subdirs;

        private Dir(String name, Dir parent) {
            this.name = name;
            this.parent = parent;
            this.files = new ArrayList<>();
            this.subdirs = new HashMap<>();
        }

        public static Dir root() {
            return new Dir("/", null);
        }

        public static Dir of(String name, Dir parent) {
            return new Dir(name, parent);
        }

        public Dir resolve(String name) {
            return subdirs.get(name);
        }

        public Dir parent() {
            return parent;
        }

        public void add(Dir dir) {
            if (!subdirs.containsKey(dir.name))
                subdirs.put(dir.name, dir);
        }

        public void add(File file) {
            files.add(file);
        }

        public List<Dir> subdirs() {
            var result = new ArrayList<>(subdirs.values());
            subdirs.values().forEach(x->result.addAll(x.subdirs()));
            return result;
        }

        public long size() {
            long fileSize = files.stream().mapToLong(File::size).sum();
            var dirSize = subdirs.values().stream().mapToLong(Dir::size).sum();
            //System.out.printf("%s [ size: %d (in %d files and %d dirs)%n", name, fileSize+dirSize, files.size(), subdirs.size());
            return fileSize+dirSize;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Dir) obj;
            return Objects.equals(this.name, that.name) &&
                    Objects.equals(this.parent, that.parent);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, parent);
        }

        @Override
        public String toString() {
            return String.format("D=%s%s, f:%d d:%d [%d]", parent!=null?parent.name:"", name, files.size(), subdirs.size(), size());
        }

    }

    record File(long size, String name) {
        public static File of(Long size, String name) {
            return new File(size, name);
        }
    }

}