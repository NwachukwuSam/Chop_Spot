if status is-interactive
    # Commands to run in interactive sessions can go here
end

string match -q "$TERM_PROGRAM" "kiro" and . (kiro --locate-shell-integration-path fish)

# Java
set -gx JAVA_HOME /opt/java/jdk-17.0.13+11

# PATH - order matters! Put custom paths first
set -gx PATH $HOME/.bun/bin $PATH
set -gx PATH /snap/bin $PATH
set -gx PATH $JAVA_HOME/bin $PATH
set -gx PATH /usr/local/sbin /usr/local/bin /usr/sbin /usr/bin /sbin /bin $PATH

# bun
set --export BUN_INSTALL "$HOME/.bun"
