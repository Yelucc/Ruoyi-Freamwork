package com.ruoyi.system.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysUrlMap;
import com.ruoyi.system.service.ISysUrlMapService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短链映射Controller
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/system/urlmap")
public class SysUrlMapController extends BaseController
{
    @Autowired
    private ISysUrlMapService sysUrlMapService;

    /**
     * 查询短链映射列表
     */
    @PreAuthorize("@ss.hasPermi('system:urlmap:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUrlMap sysUrlMap)
    {
        Page<SysUrlMap> list = sysUrlMapService.page(getPage(), Wrappers.lambdaQuery(sysUrlMap));
        return getDataTable(list);
    }

    /**
     * 导出短链映射列表
     */
    @PreAuthorize("@ss.hasPermi('system:urlmap:export')")
    @Log(title = "短链映射", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUrlMap sysUrlMap)
    {
        List<SysUrlMap> list = sysUrlMapService.list(Wrappers.lambdaQuery(sysUrlMap));
        ExcelUtil<SysUrlMap> util = new ExcelUtil<SysUrlMap>(SysUrlMap.class);
        util.exportExcel(response, list, "短链映射数据");
    }

    /**
     * 获取短链映射详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:urlmap:query')")
    @GetMapping(value = "/{shortUrl}")
    public AjaxResult getInfo(@PathVariable("shortUrl") String shortUrl)
    {
        return success(sysUrlMapService.getById(shortUrl));
    }

    /**
     * 新增短链映射
     */
    @PreAuthorize("@ss.hasPermi('system:urlmap:add')")
    @Log(title = "短链映射", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUrlMap sysUrlMap) throws NoSuchAlgorithmException {
        return toAjax(sysUrlMapService.saveAndShortenUrl(sysUrlMap));
    }

    /**
     * 修改短链映射
     */
    @PreAuthorize("@ss.hasPermi('system:urlmap:edit')")
    @Log(title = "短链映射", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUrlMap sysUrlMap)
    {
        return toAjax(sysUrlMapService.updateById(sysUrlMap));
    }

    /**
     * 删除短链映射
     */
    @PreAuthorize("@ss.hasPermi('system:urlmap:remove')")
    @Log(title = "短链映射", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shortUrls}")
    public AjaxResult remove(@PathVariable String[] shortUrls)
    {
        return toAjax(sysUrlMapService.removeBatchByIds(Arrays.asList(shortUrls)));
    }
}
