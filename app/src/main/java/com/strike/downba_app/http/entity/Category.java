package com.strike.downba_app.http.entity;

/**
 * Created by strike on 16/7/2.
 *
 * 分类的实体对象
 */
public class Category {
    private int cate_id;//类别id
    private int parent_id;//父类id
    private String cname;//分类名称
    private String cname_py;//分类字母别名
    private String ctitle;//SEO标题
    private String ckey;//SEO关键词
    private String cdesc;//SEO描述
    private String corder;//分类排序
    private String cimg;//分类图片
    private int cat_show;//是否显示分类
    private String tpl_index;//封面模板
    private String tpl_listvar;//列表变量模板
    private String tpl_content;//内容页模板
    private int type;//分类类型：应用=0，资讯=1
    private int cdata;//分类下的数据

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;//是否选中

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCname_py() {
        return cname_py;
    }

    public void setCname_py(String cname_py) {
        this.cname_py = cname_py;
    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public String getCdesc() {
        return cdesc;
    }

    public void setCdesc(String cdesc) {
        this.cdesc = cdesc;
    }

    public String getCorder() {
        return corder;
    }

    public void setCorder(String corder) {
        this.corder = corder;
    }

    public String getCimg() {
        return cimg;
    }

    public void setCimg(String cimg) {
        this.cimg = cimg;
    }

    public int getCat_show() {
        return cat_show;
    }

    public void setCat_show(int cat_show) {
        this.cat_show = cat_show;
    }

    public String getTpl_index() {
        return tpl_index;
    }

    public void setTpl_index(String tpl_index) {
        this.tpl_index = tpl_index;
    }

    public String getTpl_listvar() {
        return tpl_listvar;
    }

    public void setTpl_listvar(String tpl_listvar) {
        this.tpl_listvar = tpl_listvar;
    }

    public String getTpl_content() {
        return tpl_content;
    }

    public void setTpl_content(String tpl_content) {
        this.tpl_content = tpl_content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCdata() {
        return cdata;
    }

    public void setCdata(int cdata) {
        this.cdata = cdata;
    }
}
