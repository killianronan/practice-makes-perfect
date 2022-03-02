

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity program_counter_tb is
--  Port ( );
end program_counter_tb;

architecture Behavioral of program_counter_tb is
component program_counter is 
 Port ( 
      data_in : in std_logic_vector(15 downto 0);
      PL, PI, reset,Clk: in std_logic;
      PC_result : out std_logic_vector(15 downto 0)
     );
end component;
    --Input  
    signal data_in : std_logic_vector(15 downto 0):= x"0000";
    signal PL, PI, Clk, reset : std_logic := '0';

    --Output
    signal PC_result : std_logic_vector(15 downto 0):= x"0000";
        
begin
    UUT: program_counter
    Port Map(
        data_in => data_in,
        PI => PI,
        PL => PL,
        Clk => Clk,
        reset => reset,
        PC_result => PC_result
    );
      
sim_proc :process
begin
    --Initialize
    Clk <= '1';
    reset <= '1';
    wait for 5ns;
    Clk <= '0';
    reset <= '0';
    wait for 5ns;
    
    --Set PC to PC_in
    Clk <= '1';
    data_in <= x"0001";
    PI <= '0';
    PL <= '1';
    wait for 5ns;
    Clk <= '0';
    wait for 5ns;
    --Set PI and PL, Should increment and not change PC_result
    Clk <= '1';
    data_IN <= x"EEE1";
    PI <= '1';
    PL <= '1';
    wait for 5ns;
    Clk <= '0';
    wait for 5ns;
    
    --Increment PC by setting PI and Clk
    Clk <= '1';
    PI <= '1';
    PL <= '0';
    wait for 5ns;
    Clk <= '0';
    wait for 5ns;
    
    --PL and PI are 0, Should do nothing
    Clk <= '1';
    data_IN <= x"000F";
    PL <= '0';
    PI <= '0';
    wait for 5ns;
    Clk <= '0';
    wait for 5ns;
    
 end process;
end Behavioral;