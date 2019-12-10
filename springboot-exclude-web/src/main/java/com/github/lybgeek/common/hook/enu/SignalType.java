package com.github.lybgeek.common.hook.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SignalType {
  /**
   * 在Linux下支持的信号（具体信号kill -l命令查看）：
   * SEGV, ILL, FPE, BUS, SYS, CPU, FSZ, ABRT, INT, TERM, HUP, USR1, USR2, QUIT, BREAK, TRAP, PIPE
   * 在Windows下支持的信号：
   * SEGV, ILL, FPE, ABRT, INT, TERM, BREAK
   */
  TERM("TERM","15"),USR2("USR2","12");
  private final String type;

  /**
   *  1) HUP	 2) INT	 3) QUIT	 4) ILL	 5) TRAP
   *  6) ABRT	 7) BUS	 8) FPE	 9) KILL	10) USR1
   * 11) SEGV	12) USR2	13) PIPE	14) ALRM	15) TERM
   * 16) STKFLT	17) CHLD	18) CONT	19) STOP	20) TSTP
   * 21) TTIN	22) TTOU	23) URG	24) XCPU	25) XFSZ
   * 26) VTALRM	27) PROF	28) WINCH	29) IO	30) PWR
   * 31) SYS	34) RTMIN	35) RTMIN+1	36) RTMIN+2	37) RTMIN+3
   * 38) RTMIN+4	39) RTMIN+5	40) RTMIN+6	41) RTMIN+7	42) RTMIN+8
   * 43) RTMIN+9	44) RTMIN+10	45) RTMIN+11	46) RTMIN+12	47) RTMIN+13
   * 48) RTMIN+14	49) RTMIN+15	50) RTMAX-14	51) RTMAX-13	52) RTMAX-12
   * 53) RTMAX-11	54) RTMAX-10	55) RTMAX-9	56) RTMAX-8	57) RTMAX-7
   * 58) RTMAX-6	59) RTMAX-5	60) RTMAX-4	61) RTMAX-3	62) RTMAX-2
   * 63) RTMAX-1	64) RTMAX	
   */
  private final String desc;


  public static SignalType getSignal(String type) {

    for (SignalType signalType : values()) {
      if (signalType.getType().equals(type)) {
        return signalType;
      }
    }
    return TERM;
  }
}
