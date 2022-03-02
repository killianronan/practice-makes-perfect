library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity zero_fill_tb is
end zero_fill_tb;

architecture Behavioral of zero_fill_tb is
    component Zero_fill is
        Port ( 
            SB : in std_logic_vector(2 downto 0);
            z_out : out std_logic_vector(15 downto 0)
        );
    end component;
    --Input    
    signal SB : std_logic_vector(2 downto 0):= "000";
    --Output
    signal z_out : std_logic_vector(15 downto 0) := x"0000";
       
begin
    UUT: zero_fill
    Port Map(
        SB => SB,
        z_out => z_out
    );
    
simulation_process :process
begin
    SB <= "100";
    wait for 5ns;
    SB <= "111";
    wait for 5ns;
    end process;
end Behavioral;
