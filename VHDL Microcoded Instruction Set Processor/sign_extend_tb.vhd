library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity sign_extend_tb is
--  Port ( );
end sign_extend_tb;

architecture Behavioral of sign_extend_tb is
component sign_extend is
    Port ( SB : in  STD_LOGIC_VECTOR(2 downto 0);
           DR : in  STD_LOGIC_VECTOR(2 downto 0);
           result : out  STD_LOGIC_VECTOR(15 downto 0));
end component;

    --Input    
    signal DR : std_logic_vector(2 downto 0):= "000";
    signal SB : std_logic_vector(2 downto 0):= "000";
    
    --Output
    signal result : std_logic_vector(15 downto 0) := x"0000";
        
begin
    UUT: sign_extend
    Port Map(
        DR => DR,
        SB => SB,
        result => result
    );
    
    simulation_process :process
begin
    --zeros
    DR <= "011";
    SB <= "011";
    wait for 5ns;
    --ones
    DR <= "111";
    SB <= "111";
    wait for 5ns;        
end process;
end Behavioral;