library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity CPU_tb is
end CPU_tb;

architecture Behavioral of CPU_tb is
    component CPU is
        Port (
            reset, Clk: in std_logic;
            reg0: out std_logic_vector(15 downto 0);
            reg1: out std_logic_vector(15 downto 0);
            reg2: out std_logic_vector(15 downto 0);
            reg3: out std_logic_vector(15 downto 0);
            reg4: out std_logic_vector(15 downto 0);
            reg5: out std_logic_vector(15 downto 0);
            reg6: out std_logic_vector(15 downto 0);
            reg7: out std_logic_vector(15 downto 0)
        );
    end component;
    
    signal clk, reset : std_logic := '0';
    signal out0, out1, out2, out3, out4, out5, out6, out7 : std_logic_vector(15 downto 0);
    constant limit : integer := 420;
    constant current : integer := 0;
    constant clk_period : time := 100ns;
begin
    UTT: CPU
        Port Map (
            reset => reset,
            clk => clk,
            reg0 => out0,
            reg1 => out1,
            reg2 => out2,
            reg3 => out3,
            reg4 => out4,
            reg5 => out5,
            reg6 => out6,
            reg7 => out7
        );
process
    begin

        clk <= '1';
        reset <= '1';
        wait for 100ns;
        
        reset <= '0';
        clk <= '0';
        while limit-1 >= current loop
            clk <= not clk;
            wait for clk_period/2;
        end loop;
end process;

end Behavioral;
