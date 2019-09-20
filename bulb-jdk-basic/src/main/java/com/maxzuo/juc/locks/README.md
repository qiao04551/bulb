## Java并发包-锁

### Lock和synchronized的选择

- 1）Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；
- 2）synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock()去释放锁，
     则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
- 3）Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；
- 4）通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
- 5）Lock可以提高多个线程进行读操作的效率。
- 6）在性能上来说，如果竞争资源不激烈，两者的性能是差不多的，而当竞争资源非常激烈时（即有大量线程同时竞争），此时Lock的性能要远远优于synchronized
    
### Lock中声明了四个方法来获取锁：

- 1）Lock()方法是使用得最多的一个方法，就是用来获取锁。如果锁已被其他线程获取，则进行等待。如果采用Lock，必须主动去释放锁，并且在发生异常时，不会自动释放锁。
 因此一般来说，使用Lock必须在try{}catch{}块中进行，并且将释放锁的操作放在finally块中进行，以保证锁一定被被释放，防止死锁的发生。
- 2）tryLock()方法是有返回值的，它表示用来尝试获取锁，如果获取成功，则返回true，如果获取失败（即锁已被其他线程获取），则返回false，也就说这个方法无论如何都会立即返回。
 在拿不到锁时不会一直在那等待。
- 3）tryLock(long time, TimeUnit unit)方法和tryLock()方法是类似的，只不过区别在于这个方法在拿不到锁时会等待一定的时间，在时间期限之内如果还拿不到锁，就返回false。
 如果如果一开始拿到锁或者在等待期间内拿到了锁，则返回true。
- 4）lockInterruptibly()方法比较特殊，当通过这个方法去获取锁时，如果线程正在等待获取锁，则这个线程能够响应中断，即中断线程的等待状态。也就使说，当两个线程同时通过
 lock.lockInterruptibly()想获取某个锁时，假若此时线程A获取到了锁，而线程B只有在等待，那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程。

### 锁的相关概念介绍

**可重入锁：**

- 1.如果锁具备可重入性，则称作为可重入锁。像synchronized和ReentrantLock都是可重入锁。
- 2.举个简单的例子，当一个线程执行到某个synchronized方法时，比如说method1，而在method1中会调用另外一个synchronized方法method2，此时线程不必重新去申请锁，而是可以直接执行方法method2。

**可中断锁：**

- 1.顾名思义，就是可以相应中断的锁。在Java中，synchronized就不是可中断锁，而Lock是可中断锁。
- 2.如果某一线程A正在执行锁中的代码，另一线程B正在等待获取该锁，可能由于等待时间过长，线程B不想等待了，想先处理其他事情，
    我们可以让它中断自己或者在别的线程中中断它，这种就是可中断锁。

**公平锁：**

- 1.公平锁即尽量以请求锁的顺序来获取锁。比如同是有多个线程在等待一个锁，当这个锁被释放时，等待时间最久的线程（最先请求的线程）会获得该所，
    这种就是公平锁。非公平锁即无法保证锁的获取是按照请求锁的顺序进行的。这样就可能导致某个或者一些线程永远获取不到锁。
- 2.在Java中，synchronized就是非公平锁，它无法保证等待的线程获取锁的顺序。而对于ReentrantLock和ReentrantReadWriteLock，它默认情况下是非公平锁，但是可以设置为公平锁。

**读写锁：**

- 1.读写锁将对一个资源（比如文件）的访问分成了2个锁，一个读锁和一个写锁。正因为有了读写锁，才使得多个线程之间的读操作不会发生冲突。
- 2.ReadWriteLock就是读写锁，它是一个接口，ReentrantReadWriteLock实现了这个接口。可以通过readLock()获取读锁，通过writeLock()获取写锁。