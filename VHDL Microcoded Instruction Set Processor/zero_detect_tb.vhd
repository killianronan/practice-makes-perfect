library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity zero_detect_tb is
end zero_detect_tb;

architecture Behavioral of zero_detect_tb is
    component zero_detect is
        Port ( 
            I : in std_logic_vector(15 downto 0);
            zero : out std_logic
        );
    end component;
    --input
    signal I : std_logic_vector(15 downto 0):= x"0000";
    --output
    signal zero : std_logic := '0';
begin
    UUT: zero_detect
    Port Map(
        I => I,
        zero => zero
    );
    
sim_proc :process
begin
        --0
        I <= x"0000";
        wait for 2ns;
        --not 0
        I <= x"0A0F";
        wait for 2ns;
        --0
        I <= x"0000";
        wait for 2ns;
        --not 0
        I <= x"FFFF";
        wait for 2ns;
     end process;
end Behavioral;