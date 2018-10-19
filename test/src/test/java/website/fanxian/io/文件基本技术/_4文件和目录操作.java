package website.fanxian.io.文件基本技术;

import org.junit.Test;
import website.fanxian.GrammarCandy;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * File类中的操作大概可以分为三类：
 *      文件数据、
 *      文件操作、
 *      目录操作，
 *
 *
 */
public class _4文件和目录操作 {
    @Test
    public void _1构造方法() throws Exception{/*
        public File(String pathname) //可以是相对路径，也可以是绝对路径
        public File(String parent, String child)
        public File(File parent, String child) // parent表示父目录，child表示孩子
     */
    }
    @Test
    public void _2文件元数据() throws Exception{/*
        主要包含文件名和路径、文件基本信息以及一些安全和权限相关的信息。

        /// 文件名和路径
        public String getName()
        public boolean isAbsolute()
        public String getPath()
        public String getAbsolutePath()
        public String getCanonicalPath() throws IOException
        public String getParent()
        public File getParentFile()
        public File getAbsoluteFile()
        public File getCanonicalFile() throws IOException

        /// 分隔符
        public static final String separator
        public static final char separatorChar
        public static final String pathSeparator
        public static final char pathSeparatorChar

        /// 文件或目录
        public boolean exists() //
        public boolean isDirectory() //
        public boolean isFile() //
        public long length() //
        public long lastModified() //
        public boolean setLastModified(long time) //

        /// 安全和权限
        public boolean isHidden() //
        public boolean canExecute() //
        public boolean canRead() //
        public boolean canWrite() //
        public boolean setReadOnly() //
        //
        public boolean setReadable(boolean readable, boolean ownerOnly)
        public boolean setReadable(boolean readable)
        //
        public boolean setWritable(boolean writable, boolean ownerOnly)
        public boolean setWritable(boolean writable)
        //
        public boolean setExecutable(boolean executable, boolean ownerOnly)
        public boolean setExecutable(boolean executable)
     */
    }
    @Test
    public void _3文件操作() throws Exception{/*
        创建、删除、重命名

        // 新建一个File对象不会创建文件，要用下面的方法：
        public boolean createNewFile() throws IOException

        // 临时文件，静态方法
            prefix必需，
            suffix如果为null，则默认为.tmp
            directory如果不指定或指定为null，则使用系统默认目录
        public static File createTempFile(String prefix, String suffix)
            throws IOException
        public static File createTempFile(String prefix, String suffix,
            File directory) throws IOException

        // File类的删除方法：
            public boolean delete()
            public void deleteOnExit()

        // 删除
        public boolean delete() // 如果File是目录且不为空，则delete不会成功，返回false
        public void deleteOnExit() // 将File对象加入到待删列表，虚拟机正常退出的时候进行实际删除

        // 重命名
        public boolean renameTo(File dest)
     */
    }
    @Test
    public void _4目录操作() throws Exception {/*
        当File对象代表目录是，可以执行目录相关的操作，如 创建、遍历
            public boolean mkdir() // 中间目录不存在，失败false
            public boolean mkdirs() // 中间目录不存在，会创建必需的中间目录

            public File[] listFiles(FileFilter filter)
            public File[] listFiles(FilenameFilter filter)

            public interface FileFilter {
                boolean accept(File pathname);
            }
            public interface FilenameFilter {
                boolean accept(File dir, String name);
            }
     */
        File f = new File(".");
        File[] files = f.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".txt")){
                    return true;
                }
                return false;
            }
        });
    }

    // 计算一个目录下的所有文件的大小（包括子目录）
    public static long sizeOfDirectory(final File directory) {
        long size = 0;
        if(directory.isFile()) {
            return directory.length();
        } else {
            for(File file : directory.listFiles()) {
                if(file.isFile()) {
                    size += file.length();
                } else {
                    size += sizeOfDirectory(file);
                }
            }
        }
        return size;
    }

    // 在一个目录下，查找所有给定文件名的文件
    public static Collection<File> findFile(final File directory,
                                            final String fileName) {
        List<File> files = new ArrayList<>();
        for(File f : directory.listFiles()) {
            if(f.isFile() && f.getName().equals(fileName)) {
                files.add(f);
            } else if(f.isDirectory()) {
                files.addAll(findFile(f, fileName));
            }
        }
        return files;
    }

    // 删除非空目录
    public static void deleteRecursively(final File file) throws IOException {
        if(file.isFile()) {
            if(!file.delete()) {
                throw new IOException("Failed to delete "
                        + file.getCanonicalPath());
            }
        } else if(file.isDirectory()) {
            for(File child : file.listFiles()) {
                deleteRecursively(child);
            }
            if(!file.delete()) {
                throw new IOException("Failed to delete "
                        + file.getCanonicalPath());
            }
        }
    }
}
