package a4.command;

public interface IPlayerCommandHolder 
{//command design.  holds the commands, or sets them.
	public IPlayerCommand getCommand();
	public void setCommand(IPlayerCommand thecmd);

}
