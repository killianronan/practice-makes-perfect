library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
entity program_counter is
 Port ( 
      data_in : in std_logic_vector(15 downto 0);
      PL, PI, reset,Clk: in std_logic;
      PC_result : out std_logic_vector(15 downto 0)
     );
end program_counter;
architecture Behavioral of program_counter is
begin
    process (Clk)
        variable temp : std_logic_vector(15 downto 0);
    begin
        if(Clk = '1' and PI = '1') then
            temp := conv_std_logic_vector(1+ conv_integer(temp), 16);
        elsif(Clk = '1'and PL = '1') then
            temp := data_in+ temp;
        elsif(Clk = '1'and reset = '1') then 
        	temp := x"0000";
        end if;
        PC_result <= temp after 10ns;
    end process;
end Behavioral;