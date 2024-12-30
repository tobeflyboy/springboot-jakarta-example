package com.nutcracker.example.demo.service.sys.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import com.nutcracker.example.demo.entity.systeminfo.CpuInfo;
import com.nutcracker.example.demo.entity.systeminfo.HeapInfo;
import com.nutcracker.example.demo.entity.systeminfo.JvmInfo;
import com.nutcracker.example.demo.entity.systeminfo.MemInfo;
import com.nutcracker.example.demo.entity.systeminfo.NoHeapInfo;
import com.nutcracker.example.demo.entity.systeminfo.SysFileInfo;
import com.nutcracker.example.demo.entity.systeminfo.SysInfo;
import com.nutcracker.example.demo.entity.systeminfo.SystemHardwareInfo;
import com.nutcracker.example.demo.service.sys.SystemInfoService;
import com.nutcracker.example.demo.util.IpInfoUtils;
import lombok.Data;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * system info service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:40
 */
@Data
@Service
public class SystemInfoServiceImpl implements SystemInfoService {

    private static final int OSHI_WAIT_SECOND = 1000;

    /**
     * 获取硬件信息
     */
    @Override
    public SystemHardwareInfo getSystemInfo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.setCpuInfo(hal.getProcessor());
        systemHardwareInfo.setMemInfo(hal.getMemory());
        systemHardwareInfo.setSysInfo();
        systemHardwareInfo.setJvmInfo();
        systemHardwareInfo.setSysFiles(si.getOperatingSystem());
        return systemHardwareInfo;
    }


    @Override
    public CpuInfo getCpuInfo(CentralProcessor processor) {
        CpuInfo cpu = new CpuInfo();
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
        return cpu;
    }

    @Override
    public MemInfo getMemInfo(GlobalMemory memory) {
        MemInfo mem = new MemInfo();
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
        return mem;
    }

    @Override
    public JvmInfo getJvmInfo() {
        JvmInfo jvm = new JvmInfo();
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
        return jvm;
    }

    @Override
    public HeapInfo getHeapInfo() {
        HeapInfo heapInfo = new HeapInfo();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        heapInfo.setHeapInit(heapMemoryUsage.getInit());
        heapInfo.setHeapCommitted(heapMemoryUsage.getCommitted());
        heapInfo.setHeapMax(heapMemoryUsage.getMax());
        heapInfo.setHeapUsed(heapMemoryUsage.getUsed());
        return heapInfo;
    }

    @Override
    public NoHeapInfo getNoHeapInfo() {
        NoHeapInfo noHeapInfo = new NoHeapInfo();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        noHeapInfo.setNoHeapCommitted(nonHeapMemoryUsage.getCommitted());
        noHeapInfo.setNoHeapInit(nonHeapMemoryUsage.getInit());
        noHeapInfo.setNoHeapMax(nonHeapMemoryUsage.getMax());
        noHeapInfo.setNoHeapUsed(nonHeapMemoryUsage.getUsed());
        return noHeapInfo;
    }

    @Override
    public SysInfo getSysInfo() {
        SysInfo sys = new SysInfo();
        Properties props = System.getProperties();
        sys.setComputerName(IpInfoUtils.getHostName());
        sys.setComputerIp(NetUtil.getLocalhostStr());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
        return sys;
    }

    @Override
    public List<SysFileInfo> getSysFileInfo(OperatingSystem os) {
        List<SysFileInfo> sysFiles = new ArrayList<>();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFileInfo sysFile = new SysFileInfo();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            if (total == 0) {
                sysFile.setUsage(0);
            } else {
                sysFile.setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100));
            }
            sysFiles.add(sysFile);
        }
        return sysFiles;
    }


    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    private String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }


}
