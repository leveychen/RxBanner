package cn.levey.rxbanner.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Levey on 2018/4/3 10:02.
 * e-mail: m@levey.cn
 */

public class FakeData {


    public static List<String> FAKE_DATA(){
        List<String> list = new ArrayList<>();
        final int size = (int)Math.round(Math.random()* 10 + 2);
        int[] rd = getRandom(FAKE_IMAGES.length - 1);
        for (int i = 0; i < size; i++) {
            list.add(FAKE_IMAGES[rd[i]]);
        }
        return list;
    }

    //weibo.com
    private static final String[] FAKE_IMAGES = {
            "http://ww2.sinaimg.cn/large/0060lm7Tly1fpz8mmlnezj31hc0u011t.jpg",
            "http://ww4.sinaimg.cn/large/0060lm7Tly1fpz8mn6l0wj31hc0u0wjd.jpg",
            "http://ww2.sinaimg.cn/large/0060lm7Tly1fpz8mpkngcj31hc0u0guy.jpg",
            "http://ww1.sinaimg.cn/large/0060lm7Tly1fpz8mrse94j31hc0u0dml.jpg",
            "http://ww2.sinaimg.cn/large/0060lm7Tly1fpz8mrvksij31hc0u046c.jpg",
            "http://ww2.sinaimg.cn/large/0060lm7Tly1fpz8ms6p4bj31hc0u0nbl.jpg",
            "http://ww2.sinaimg.cn/large/0060lm7Tly1fpz8ms7nvwj31hb0u0475.jpg",
            "http://ww1.sinaimg.cn/large/0060lm7Tly1fpz8mt5uh6j31hc0u0nia.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fq7kdjfht6j31hc0xcgvg.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fq7kdp36mzj31hc0xcqe0.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fq7kdq9gi8j31hc0xcgys.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fq7kdtug92j31hc0xc7kr.jpg",
            "http://wx2.sinaimg.cn/large/0060lm7Tly1fq7kdtun5fj31hc0xcqlg.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fq7kdui5t4j31hc0xcgu3.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fq7kduzjiqj31hc0xc0zn.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fq7ke1pedyj31hc0xc10m.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fq7ke1ws4ij31hc0xcaik.jpg",
            "http://wx2.sinaimg.cn/large/0060lm7Tly1fq7ke1tjmej31hc0xc4p5.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fq7ke491rgj31hc0xc1hj.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fq7ke4mz3gj31hc0xcwzc.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fq7kehc226j31hc0xcx52.jpg",
            "http://wx2.sinaimg.cn/large/0060lm7Tly1fqh407g9u4j31hc0u0qbo.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fqh408bgxpj31hc0u07d8.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fqh40coyu8j31hc0u0h6m.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqh40lcvb8j31hc0u07rj.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fqh40ukc9oj31hc0u0b13.jpg",
            "http://wx2.sinaimg.cn/large/0060lm7Tly1fqh40w4glpj31hc0u04qp.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fqh40xew2gj31hc0u01kx.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fqh40y7nm6j31hc0u07wh.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fqh4107gldj31hc0u0nfi.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fqh4116mt3j31hc0u0qd2.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqh414sullj31hc0u0dnw.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fqh418uk7aj31hc0u0tsg.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqh419gh9cj31hc0u0dkp.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqh41axcyrj31hc0xcdt8.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fqh41cz8nfj31hc0xce81.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fqh41d1svtj31hc0xctsn.jpg",
            "http://wx4.sinaimg.cn/large/0060lm7Tly1fqh428dcqxj31kw0w0b2a.jpg",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqh427oec2j31kw0zkqv6.jpg",
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fqh429awxaj31hc0u0qv7.jpg"
};


    public static final String[] FAKE_GUIDE = {
            "http://wx1.sinaimg.cn/large/0060lm7Tly1fqav85d0j4j30u01hcgma.jpg?id=1",
            "http://wx2.sinaimg.cn/large/0060lm7Tly1fqav85maq5j30u01hcmxx.jpg?id=2",
            "http://wx2.sinaimg.cn/large/0060lm7Tly1fqav85lis5j30u01hcq3p.jpg?id=3",
            "http://wx2.sinaimg.cn/large/0060lm7Tly1fqav85l0pcj30u01hct9g.jpg?id=4",
            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqav85m4vpj30u01hcaat.jpg?id=5",
//            "http://wx1.sinaimg.cn/large/0060lm7Tly1fqav85lcnsj30u01hcmxy.jpg?id=6",
//            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqav85vjy1j30u01hcgmc.jpg?id=7",
//            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqav85x8h1j30u01hcdgn.jpg?id=8",
//            "http://wx4.sinaimg.cn/large/0060lm7Tly1fqav85zp5sj30u01hcq3q.jpg?id=9",
//            "http://wx3.sinaimg.cn/large/0060lm7Tly1fqav85xr4dj30u01hcwfd.jpg?id=10",
    };

    private static int[] getRandom(int len){
        Random rd = new Random();
        int[] rds = new int[len];
        final List<Integer> lst = new ArrayList<>();
        int index;
        for(int i=0;i<len;i++){
            lst.add(i);
        }
        for(int i=0;i<len;i++){
            index=rd.nextInt(len-i);
            rds[i]=lst.get(index);
            lst.remove(index);
        }
        return rds;
    }
}
