package model;

import java.io.Serializable;

public enum State implements Serializable {
    NOT_REALIZED, COMPLETED, IN_PROGRESS, CANCELLED, DELAYED;
}
